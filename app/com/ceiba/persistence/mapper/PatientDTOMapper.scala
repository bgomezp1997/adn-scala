package com.ceiba.persistence.mapper

import com.ceiba.model.dto.PatientDTO

object PatientDTOMapper {

  implicit def fromResultToPatientDTO(datos: (Long, String, String, Option[java.sql.Date], String, String, Int)): PatientDTO =
    PatientDTO(datos._1, Some(datos._2), Some(datos._3), datos._4.map(d => d.toLocalDate.atStartOfDay()), Some(datos._5), Some(datos._6), Some(datos._7))

  implicit def toGenerationMap(register: Seq[(Long, String, String, Option[java.sql.Date], String, String, Int)]): List[PatientDTO] =
    register.map(fromResultToPatientDTO).toList

}
