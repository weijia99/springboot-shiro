package com.sxt.sys.common;


import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {
//    生成有children的list<treenode>的返回
    /*
    *单个treenode生成有子节点的
    * 先开始是找出所有的pid为1的id加入进去，然后里面id有（1-6），剩下id威7-11的
    * pid有在list里面，所以2,6也有子节点
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes ,Integer topId){
        List<TreeNode> nodes=new ArrayList<>();
        for (TreeNode n1 :
                treeNodes) {
//            这一步是加入pid为1的节点
            if (n1.getPid()==topId){
                nodes.add(n1);


            }
//          这一步是从list找到上面的子节点是pid为2-6
            for (TreeNode n2 :
                    treeNodes) {
                if (n2.getPid() == n1.getId()) {
                    n1.getChildren().add(n2);
                }
                }
        }
        return nodes;
    }
}
