package domen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovableProperty {
	private Long id;
	private String name;
	private BigDecimal cost;
	List<RiskType> riskTypes;
}
