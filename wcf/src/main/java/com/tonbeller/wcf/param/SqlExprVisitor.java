package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public interface SqlExprVisitor {
  void visitSqlAndExpr(SqlAndExpr exp);
  void visitSqlBetweenExpr(SqlBetweenExpr exp);
  void visitSqlEqualExpr(SqlEqualExpr exp);
  void visitSqlInExpr(SqlInExpr exp);
  void visitSqlOrExpr(SqlOrExpr exp);
  void visitSqlDummyExpr(SqlDummyExpr expr);
}
