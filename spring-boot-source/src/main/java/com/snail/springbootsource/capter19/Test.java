//package com.snail.springbootsource.capter19;
//
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class Test {
//
//    private PlatformTransactionManager platformTransactionManager;
//
//    /**
//     * 使用spring原始的事务伪代码
//     * @param platformTransactionManager
//     */
//    public void originalSpring(){
//        TransactionDefinition transactionDefinition = null;
//        TransactionStatus transaction = getPlatformTransactionManager().getTransaction(transactionDefinition);
//        try{
//            //执行业务逻辑
//        }catch (Exception e){
//            platformTransactionManager.rollback(transaction);
//        }
//    }
//
//    public PlatformTransactionManager getPlatformTransactionManager() {
//        return platformTransactionManager;
//    }
//
//    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
//        this.platformTransactionManager = platformTransactionManager;
//    }
//
//    /**
//     * 原始的使用jdbc管理事务的方式
//     */
//    public void originalPattern(){
//        Connection connection;
//        try {
//            connection = DriverManager.getConnection("xxxx","root","123456");
//            connection.setAutoCommit(false);
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
////            try {
//////                connection.rollback();
////            } catch (SQLException e1) {
////                try {
////                    connection.close();
////                } catch (SQLException e2) {
////                    e2.printStackTrace();
////                }
////            }
//        }finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
