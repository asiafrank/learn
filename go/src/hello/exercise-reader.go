package main

// 练习：Reader
// 实现一个 Reader 类型，它产生一个 ASCII 字符 'A' 的无限流。

import "golang.org/x/tour/reader"

type MyReader struct{}

func (r MyReader) Read(p []byte) (n int, err error) {
	for i := 0; i < len(p); i++ {
		p[i] = 'A'
	}
	return len(p), nil
}

func main() {
	reader.Validate(MyReader{})
}
