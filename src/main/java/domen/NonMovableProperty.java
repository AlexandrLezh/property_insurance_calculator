package domen;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class NonMovableProperty {
	private Long id;
	private String name;
	private BigDecimal cost;
	private List<RiskType> riskTypes;
	private List<MovableProperty> movableProperties;

	public NonMovableProperty(Long id, String name, BigDecimal cost, List<RiskType> riskTypes, List<MovableProperty> movableProperties) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.riskTypes = riskTypes;
		this.movableProperties = Optional.ofNullable(movableProperties).orElse(Collections.emptyList());
	}

	public Optional<List<MovableProperty>> getMovableProperties() {
		return Optional.ofNullable(movableProperties);
	}
}
