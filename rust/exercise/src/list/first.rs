// 一个糟糕的栈

use std::mem;

pub struct List {
    head: Link,
}

enum Link {
    Empty,
    More(Box<Node>),
}

struct Node {
    elem: i32,
    next: Link,
}

impl List {
    pub fn new() -> Self {
        List {head: Link::Empty}
    }

    pub fn push(&mut self, elem: i32) {
        let new_node = Box::new(Node {
            elem: elem,
            next: mem::replace(&mut self.head, Link::Empty),
        });

        self.head = Link::More(new_node);
    }

    pub fn pop(&mut self) -> Option<i32> {
        match mem::replace(&mut self.head, Link::Empty) {
            Link::Empty => None,
            Link::More(boxed_node) => {
                let node = *boxed_node;
                self.head = node.next;
                Some(node.elem)
            }
        }
    }
}

impl Drop for List {
    fn drop(&mut self) {
        let mut cur_link = mem::replace(&mut self.head, Link::Empty);
        // `while let` == “在这个模式不匹配之前持续循环”
        while let Link::More(mut boxed_node) = cur_link {
            cur_link = mem::replace(&mut boxed_node.next, Link::Empty);
            // boxed_node在这里退出作用域然后被丢弃；
            // 但是其节点的`next`字段被设置为 Link::Empty
            // 所以没有多层递归产生。
        }
    }
}

mod test {
    use crate::list::first::List;

    #[test]
    fn basics() {
        let mut list = List::new();
        // 检查空列表行为正确
        assert_eq!(list.pop(), None);

        // 填充列表
        list.push(1);
        list.push(2);
        list.push(3);

        // 检查通常的移除
        assert_eq!(list.pop(), Some(3));
        assert_eq!(list.pop(), Some(2));

        // 推入更多元素来确认没有问题
        list.push(4);
        list.push(5);

        // 检查通常的移除
        assert_eq!(list.pop(), Some(5));
        assert_eq!(list.pop(), Some(4));

        // 检查完全移除
        assert_eq!(list.pop(), Some(1));
        assert_eq!(list.pop(), None);
    }
}