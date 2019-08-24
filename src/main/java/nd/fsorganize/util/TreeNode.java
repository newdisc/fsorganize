package nd.fsorganize.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeNode<T> implements ICacheTransient {
    @JsonIgnore
    private TreeNode<T> parent;
    private T data;
    @JsonIgnore
    protected Map<String, TreeNode<T>> childrenMap = new HashMap<>();
    protected final String name;
    protected List<TreeNode<T>> children = new ArrayList<>();
    protected long value = 0;
    @JsonCreator
    public TreeNode(@JsonProperty("name") final String nm){
        name = nm;
    }
    public TreeNode(final TreeNode<T> par, final String nm){
        parent = par;
        name = nm;
    }
    /**
     * Update Transient variables
     */
    public void updateTransient() {
        for( final TreeNode<T> curChild: children) {
            curChild.setParent(this);
            childrenMap.put(name, curChild);
            curChild.updateTransient();
        }
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
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }
    public String getName() {
        return name;
    }
}
