package nd.fsorganize.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class TreeNode<T> {
	@JsonIgnore
	private final TreeNode<T> parent;
	@JsonIgnore
	private T data;
	@JsonIgnore
	protected Map<String, TreeNode<T>> childrenMap = new HashMap<String, TreeNode<T>>();
	protected final String name;
	protected List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();
	protected long value = 0;
}
