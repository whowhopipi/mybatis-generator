package ${serviceImplPackage};

import ${entityPackage}.${entityName};
import ${daoPackage}.${daoName};
import ${servicePackage}.${serviceName};
import org.springframework.stereotype.Service;
import ${serviceImplParentClass};

@Service
public class ${serviceImplName} extends ${serviceImplParentClass}<${daoName}, ${entityName}> implements ${serviceName} {

}
