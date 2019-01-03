package com.snail.springbootsource.capter19;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransactionManager implements PlatformTransactionManager {

    private DataSource dataSource;

    public JdbcTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        try {
            Connection connection = dataSource.getConnection();
            TransactionResourceManage.bindResource(connection);
            return new DefaultTransactionStatus(connection,true,true,false,true,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        try {
            ((Connection)TransactionResourceManage.getResource()).commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        try {
            ((Connection)TransactionResourceManage.unbindResource()).rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
