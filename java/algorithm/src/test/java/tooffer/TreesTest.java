package tooffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreesTest {

    @Test
    public void rebuildTreeTest() {
        int[] preOrder = {1,2,4,7,3,5,6,8};
        int[] inOrder = {4,7,2,1,5,3,8,6};

        Trees.Node root = Trees.rebuildTree(preOrder, inOrder);
        Assertions.assertEquals(1, root.value);
    }
}
