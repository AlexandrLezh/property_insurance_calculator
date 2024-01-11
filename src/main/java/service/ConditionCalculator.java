package service;

import domen.Policy;
import java.math.BigDecimal;

public interface ConditionCalculator {

	BigDecimal calculateByConditions(Policy policy) ;
}
