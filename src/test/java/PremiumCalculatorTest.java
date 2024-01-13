import domen.*;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PremiumCalculatorTest {
	private final PremiumCalculator premiumCalculator = new PremiumCalculator();
	@Test
	public void someSubObjectFireTheft() {

		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"100.00",
				List.of(RiskType.FIRE));

		MovableProperty tv = createMovableProperty(1L,
				"Sony",
				"8.00",
				List.of(RiskType.THEFT));

		NonMovableProperty property1 = createProperty(1L,
				"House", List.of(pc, tv));

		Policy policy = createPolicy("LV-1",
				StatusOfPolicy.REGISTERED,
				List.of(property1));

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("2.28").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}
	@Test
	public void twoSubObjectFireTheft() {
		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"100.00",
				List.of(RiskType.FIRE));

		MovableProperty tv = createMovableProperty(1L,
				"Sony",
				"8.00",
				List.of(RiskType.THEFT));

		NonMovableProperty property1 = createProperty(1L,
				"House", List.of(pc, tv));

		Policy policy = createPolicy("LV-1",
				StatusOfPolicy.REGISTERED,
				List.of(property1));

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("2.28").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}
	@Test
	public void oneObjectFire() {
		NonMovableProperty property1 = createProperty(1L,
				"House",
				"1000.00",
				List.of(RiskType.FIRE));

		Policy policy = createPolicy("LV-2",
				StatusOfPolicy.REGISTERED,
				List.of(property1));

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("24").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	@Test
	public void oneObjectOneSubObjectFireTheft() {
		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"500.00",
				List.of(RiskType.FIRE));

		NonMovableProperty property1 = createProperty(1L,
				"House",
				"500.00",
				List.of(RiskType.FIRE),
				List.of(pc));

		Policy policy = createPolicy("LV-2",
				StatusOfPolicy.REGISTERED,
				List.of(property1));

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("24").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	@Test
	public void twoObjectsOneSubObjectFireTheft() {
		MovableProperty pc = createMovableProperty(1L,
				"McBook",
				"500.00",
				List.of(RiskType.FIRE));

		NonMovableProperty property1 = createProperty(1L,
				"House",
				"500.00",
				List.of(RiskType.FIRE),
				List.of(pc));

		MovableProperty fridge = createMovableProperty(2L,
				"Fridge",
				"500.00",
				List.of(RiskType.FIRE));

		NonMovableProperty property2 = createProperty(2L,
				"House 2",
				"500.00",
				List.of(RiskType.FIRE),
				List.of(fridge));

		Policy policy = createPolicy("LV-1",
				StatusOfPolicy.REGISTERED,
				List.of(property1, property2));

		BigDecimal premium = premiumCalculator.calculate(policy);

		assertEquals(new BigDecimal("48").stripTrailingZeros(),
				premium.stripTrailingZeros());
	}

	public static MovableProperty createMovableProperty(Long id,
	                                                    String name,
	                                                    String value,
	                                                    List<RiskType> riskType) {
		MovableProperty movableProperty = new MovableProperty();
		movableProperty.setId(id);
		movableProperty.setName(name);
		movableProperty.setCost(new BigDecimal(value));
		movableProperty.setRiskTypes(riskType);
		return movableProperty;
	}

	public static NonMovableProperty createProperty(Long id,
	                                                String name,
	                                                String value,
	                                                List<RiskType> riskType,
	                                                List<MovableProperty> movableProperties) {
		NonMovableProperty property = new NonMovableProperty();
		property.setId(id);
		property.setName(name);
		property.setCost(new BigDecimal(value));
		property.setRiskTypes(riskType);
		property.setMovableProperties(movableProperties);
		return property;
	}
	public static NonMovableProperty createProperty(Long id,
	                                                String name,
	                                                String value,
	                                                List<RiskType> riskType) {
		NonMovableProperty property = new NonMovableProperty();
		property.setId(id);
		property.setName(name);
		property.setCost(new BigDecimal(value));
		property.setRiskTypes(riskType);
		return property;
	}
	public static NonMovableProperty createProperty(Long id,
	                                                String name,
	                                                List<MovableProperty> movableProperties) {
		NonMovableProperty property = new NonMovableProperty();
		property.setId(id);
		property.setName(name);
		property.setMovableProperties(movableProperties);
		return property;
	}

	public static Policy createPolicy(String policyNumber,
	                                  StatusOfPolicy status,
	                                  List<NonMovableProperty> properties) {
		Policy policy = new Policy();
		policy.setId(policyNumber);
		policy.setStatus(status);
		policy.setNonMovableProperties(properties);
		return policy;
	}


}