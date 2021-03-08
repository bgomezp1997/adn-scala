package com.ceiba.config.persistence

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait SlickDao extends HasDatabaseConfigProvider[JdbcProfile] with SlickQueryOps {

  type SimpleJoin[A, B] = List[(A, List[B])]
  type DoubleJoin[A, B, C] = List[(A, SimpleJoin[B, C])]

  protected def resolveLeftJoin[A, B](as: Seq[(A, Option[B])]): SimpleJoin[A, B] = for {
    (x1, y1) <- as.groupBy(_._1).toList
    x2 = y1 collect { case (_, Some(x)) => x } toList
  } yield (x1, x2)

  protected def resolveDoubleLeftJoin[A, B, C](doubleJoin: Seq[(A, Option[(B, Option[C])])]): DoubleJoin[A, B, C] = for {
    (a, as) <- resolveLeftJoin(doubleJoin)
  } yield (a, resolveLeftJoin(as))

}
