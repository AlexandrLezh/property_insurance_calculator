package domen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {
	private String id;
	private StatusOfPolicy status;
	private List<NonMovableProperty> nonMovableProperties;
}
