import com.ceiba.domain.persistence.dao.PatientDAO
import com.ceiba.domain.persistence.repository.PatientRepository
import com.ceiba.infra.persistence.dao.PatientDAOImpl
import com.ceiba.infra.persistence.repository.PatientRepositoryImpl
import com.ceiba.domain.service.PatientService
import com.google.inject.AbstractModule

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[PatientRepository]).to(classOf[PatientRepositoryImpl]).asEagerSingleton()
    bind(classOf[PatientDAO]).to(classOf[PatientDAOImpl]).asEagerSingleton()
  }

}
