package com.yf.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

public class YFUUIDGenerator implements IdentifierGenerator,Configurable {

    public static final String PREFIX = "genius_";
    public static final String SUFFIX = "_fool";
    private static final Logger LOG = LogManager.getLogger(YFUUIDGenerator.class);

    private String prefix;
    private String suffix;
    private int sep;


    @Override
    public Serializable generate(SessionImplementor session, Object obj) {
//        sep = UUID.randomUUID().toString();
        PreparedStatement pst;
        Connection connection = session.connection();
        try {
            pst = connection.prepareStatement("select next_val from hibernate_sequence");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sep = rs.getInt(1);
            }
            pst = session.connection().prepareStatement("update hibernate_sequence set next_val = "+(sep+1));
            pst.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return prefix+sep+suffix;
    }


    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        prefix=params.getProperty("hibernate.YFUUIDGenerator.prefix",PREFIX);
        suffix=params.getProperty("hibernate.YFUUIDGenerator.suffix",SUFFIX);
    }
}
