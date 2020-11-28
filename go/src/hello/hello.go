package main

import "fmt"

type Obj struct {
	name string
}

func main() {
	s := "hello"

	arr := make([]string, 1)
	arr[0] = s

	p := &arr[0]

	*p = "go for it"

	fmt.Println(arr)


	m := make(map[string]int)

	x := m["xxx"]
	fmt.Println(x)

	// slice
	y := make([]int, 0)
	y = append(y, 1, 3, 4)
	fmt.Println(y[0])
}
