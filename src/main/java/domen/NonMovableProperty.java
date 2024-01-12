package domen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonMovableProperty {
	private Long id;
	private String name;
	private BigDecimal cost;
	private List<RiskType> riskTypes;
	private List<MovableProperty> movableProperties;

	public NonMovableProperty(Long id, String name, BigDecimal cost, List<RiskType> riskTypes) {
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.riskTypes = riskTypes;
	}
}
