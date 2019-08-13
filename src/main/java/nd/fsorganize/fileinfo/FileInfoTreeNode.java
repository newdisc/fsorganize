package nd.fsorganize.fileinfo;


import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nd.fsorganize.util.JSONFileDAO;
import nd.fsorganize.util.TreeNode;

public class FileInfoTreeNode extends TreeNode<FileInfo> {
    private static Logger log = LoggerFactory.getLogger(FileInfoTreeNode.class);

    public FileInfoTreeNode(TreeNode<FileInfo> parent, final String nm) {
        super(parent, nm);
    }

    public void populateTree(final List<FileInfo> ret, final String rootName) {
        ret.stream().forEach(fi ->  populateTreeNode(fi, rootName) );
        log.debug("Json of fileinfo: {}", JSONFileDAO.objectToJson(this));
    }

    private void populateTreeNode(FileInfo fi, final String rootName) {
        final String[] parts = getParts(fi.getName(), rootName);
        TreeNode<FileInfo> curtn = populatePathTreeNode(parts);
        if (curtn.getData() != null) {
            log.error("Overwriting data: {}", curtn.getData());
        }
        curtn.setData(fi);
        if (FileInfo.Type.DIRECTORY == fi.getType()) {
            curtn.setValue(0);
        } else {
            curtn.setValue(fi.getBytes());
        }
        log.debug("Parts done: {} Bytes: {} Value: {}" + curtn.getData().getName(), fi.getBytes(), curtn.getValue());
    }

    @java.lang.SuppressWarnings("squid:S3824")
    private TreeNode<FileInfo> populatePathTreeNode(final String[] parts) {
        TreeNode<FileInfo> curtn = this;
        for (final String part: parts) {
            final Map<String, TreeNode<FileInfo>> children = curtn.getChildrenMap();
            final List<TreeNode<FileInfo>> chillst = curtn.getChildren();
            TreeNode<FileInfo> nexttn;
            nexttn = children.get(part);
            if (null == nexttn) {
                log.debug("Creating node for: {}", part);
                nexttn = new TreeNode<>(curtn, part);
                children.put(part, nexttn);
                chillst.add(nexttn);
            }
            curtn = nexttn;
            log.debug("Part: {}", part);
        }
        return curtn;
    }

    private static String[] getParts(final String full, final String rootName) {
        final String rootfinm = rootName;//HAS TO BE CANONICAL NAME
        final int rootfilen = rootfinm.length();
        String[] parts = new String[0];
        if (!full.contentEquals(rootfinm)) {
            final String last = full.substring(rootfilen + 1);
            log.debug(last);
            parts = last.split("\\" + File.separator);
        }
        return parts;
    }
}
