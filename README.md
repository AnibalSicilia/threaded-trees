# threaded-trees

The threaded tree of a binary tree is obtained by setting every null left child
to the predecessor of the node in the inorder traversal and every null right child
to the successor of the node in the inorder traversal.
It is important that we distinguish between the solid and the dashed lines.
For this reason we use the boolean fields lThread and rThread which are true when
the respective child is a thread.
