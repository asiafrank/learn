package main

// 练习：斐波纳契闭包
// 让我们用函数做些好玩的事情。
// 实现一个 fibonacci 函数，它返回一个函数（闭包），
// 该闭包返回一个斐波纳契数列 `(0, 1, 1, 2, 3, 5, ...)`。

import "fmt"

// 返回一个“返回int的函数”
func fibonacci() func() int {
	a := 0
	b := 1
	return func() int {
		ret := a
		c := a + b
		a = b
		b = c
		return ret
	}
}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}
