package main

import "golang.org/x/tour/pic"

func Pic(dx, dy int) [][]uint8 {
	arr_y := make([][]uint8, dy)
	for y := 0; y < cap(arr_y); y++ {
		ySlice := make([]uint8, dx)
		arr_y[y] = ySlice
		for x := 0; x < cap(ySlice); x++ {
			ySlice[x] = uint8((x + y) / 2)
		}
	}
	return arr_y
}

func main() {
	pic.Show(Pic)
}
