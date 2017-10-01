package com.github.marschall.storedprocedureproxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;

import com.github.marschall.storedprocedureproxy.ProcedureCallerFactory.ParameterRegistration;
import com.github.marschall.storedprocedureproxy.configuration.DerbyConfiguration;
import com.github.marschall.storedprocedureproxy.procedures.DerbyProcedures;

@ContextConfiguration(classes = DerbyConfiguration.class)
public class DerbyTest extends AbstractDataSourceTest {

  private DerbyProcedures functions(ParameterRegistration parameterRegistration) {
    return ProcedureCallerFactory.of(DerbyProcedures.class, this.getDataSource())
            .withParameterRegistration(parameterRegistration)
            .withNamespace()
            .build();
  }

  public static Stream<ParameterRegistration> parameters() {
    return Stream.of(ParameterRegistration.INDEX_ONLY, ParameterRegistration.INDEX_AND_TYPE);
  }

  @ParameterizedTest
  @MethodSource("parameters")
  public void outParameter(ParameterRegistration parameterRegistration) {
    assertThat(this.functions(parameterRegistration).calculateRevenueByMonth(9, 2016), comparesEqualTo(new BigDecimal(201609)));
  }

  @ParameterizedTest
  @MethodSource("parameters")
  public void inOutParameter(ParameterRegistration parameterRegistration) {
    assertThat(this.functions(parameterRegistration).raisePrice(new BigDecimal("10.1")), comparesEqualTo(new BigDecimal("20.2")));
  }

  @ParameterizedTest
  @MethodSource("parameters")
  public void returnValue(ParameterRegistration parameterRegistration) {
    assertEquals(0.01d, 6.0d, this.functions(parameterRegistration).salesTax(100.0d));
  }

}
