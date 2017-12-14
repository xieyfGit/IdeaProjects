package com.yf.spring.springdata;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;

public class MyNamingStrategy implements PhysicalNamingStrategy,Serializable {
    private static final long serialVersionUID = -8851351726385551723L;

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        StringBuilder sb = new StringBuilder("TB_");
        sb.append(name.getText().toUpperCase());
        Identifier tbName = new Identifier(sb.toString(),false);
        return tbName;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        String str = name.getText();
        StringBuilder sb = new StringBuilder();
        char c = 0;
        for (int i = 0; i <str.length() ; i++) {
            c=str.charAt(i);
            if(Character.isUpperCase(c)){
                sb.append("_");
            }
            sb.append(c);
        }
        return new Identifier(sb.toString().toUpperCase(),false);
    }
}
