package nd.fsorganize.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TreeNode<T> {
    @JsonIgnore
    private final TreeNode<T> parent;
    private T data;
    @JsonIgnore
    protected Map<String, TreeNode<T>> childrenMap = new HashMap<>();
    protected final String name;
    protected List<TreeNode<T>> children = new ArrayList<>();
    protected long value = 0;
    public TreeNode(final TreeNode<T> par, final String nm){
        parent = par;
        name = nm;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public Map<String, TreeNode<T>> getChildrenMap() {
        return childrenMap;
    }
    public List<TreeNode<T>> getChildren() {
        return children;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
    public TreeNode<T> getParent() {
        return parent;
    }
    public String getName() {
        return name;
    }
}
