package main

import (
	"fmt"
	"math"
)

// 牛顿法：https://zh.wikipedia.org/wiki/%E7%89%9B%E9%A1%BF%E6%B3%95
func Sqrt(x float64) float64 {
	var z = x
	for i := 0; i < 10; i++ {
		z -= (z*z - x) / (2 * x)
		fmt.Println("z:", z)
	}
	return z
}

func main() {
	fmt.Println(math.Sqrt(2))
	fmt.Println(Sqrt(2))
}
