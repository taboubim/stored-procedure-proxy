package com.github.marschall.storedprocedureproxy.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Defines the name of a stored procedure.
 *
 * @see <a href="https://github.com/marschall/stored-procedure-proxy/wiki/Deriving-Names">Deriving Names</a>
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ProcedureName {

  /**
   * Defines the name of the stored procedure.
   *
   * @return the name of the stored procedure
   */
  String value();

}
