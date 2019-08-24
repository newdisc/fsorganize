package nd.fsorganize.fileinfo;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.util.JSONFileDAO;
import nd.fsorganize.util.TreeNode;

public class FileInfoTreeNode extends TreeNode<FileInfo> {
    //This is the ROOT Node of the file Information tree
    private static Logger log = LoggerFactory.getLogger(FileInfoTreeNode.class);

    public FileInfoTreeNode(TreeNode<FileInfo> parent, final String nm) {
        super(parent, nm);
    }

    public FileInfoTreeNode() {
        super(null, null);
    }

    public void populateTree(final List<FileInfo> ret, final String rootName) {
        ret.stream().forEach(fi ->  populateTreeNode(fi, rootName) );
        if (log.isDebugEnabled()) {
            log.debug("Json of fileinfo: {}", JSONFileDAO.objectToJsonS(this));
        }
    }

    private void populateTreeNode(FileInfo fi, final String rootName) {
        TreeNode<FileInfo> curtn;
        if (!".".equals(fi.getName())) {
            final Path parts = Paths.get(fi.getName());
            curtn = populatePathTreeNode(parts);
        } else {
            curtn = this;
        }
        if (curtn.getData() != null) {
            log.error("Overwriting data: {}", curtn.getData());
        }
        curtn.setData(fi);
        if (FileInfo.Type.DIRECTORY == fi.getType()) {
            curtn.setValue(0);
        } else {
            curtn.setValue(fi.getBytes());
        }
        log.debug("Parts done: {} Bytes: {} Value: {}", curtn.getData().getName(), fi.getBytes(), curtn.getValue());
    }

    @java.lang.SuppressWarnings("squid:S3824")
    private TreeNode<FileInfo> populatePathTreeNode(final Path parts) {
        TreeNode<FileInfo> curtn = this;
        for (final Path part: parts) {
            final Map<String, TreeNode<FileInfo>> children = curtn.getChildrenMap();
            final List<TreeNode<FileInfo>> chillst = curtn.getChildren();
            TreeNode<FileInfo> nexttn;
            final String partnm = part.toString();
            nexttn = children.get(partnm);
            if (null == nexttn) {
                log.warn("Creating node for: {}", partnm);
                nexttn = new TreeNode<>(curtn, partnm);
                children.put(partnm, nexttn);
                chillst.add(nexttn);
            }
            curtn = nexttn;
            log.debug("Part: {}", part);
        }
        return curtn;
    }
}
