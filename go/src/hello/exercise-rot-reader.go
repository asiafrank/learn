package main

import (
	"io"
	"os"
	"strings"
)

// 练习：rot13Reader
// 有种常见的模式是一个 io.Reader 包装另一个 io.Reader，然后通过某种方式修改其数据流。

// 例如，gzip.NewReader 函数接受一个 io.Reader（已压缩的数据流）
// 并返回一个同样实现了 io.Reader 的 *gzip.Reader（解压后的数据流）。

// 编写一个实现了 io.Reader 并从另一个 io.Reader 中读取数据的 rot13Reader，
// 通过应用 rot13 代换密码对数据流进行修改。

// rot13Reader 类型已经提供。实现 Read 方法以满足 io.Reader。

type rot13Reader struct {
	r io.Reader
}

func (rr rot13Reader) Read(p []byte) (n int, err error) {
	tmp := make([]byte, len(p))
	n, err = rr.r.Read(tmp)
	if err != nil {
		return
	}
	for index, b := range tmp {
		p[index] = rot13(b)
	}
	return
}

func rot13(x byte) byte {
	capital := x >= 'A' && x <= 'Z'
	if !capital && (x < 'a' || x > 'z') {
		return x // Not a letter
	}

	x += 13
	if capital && x > 'Z' || !capital && x > 'z' {
		x -= 26
	}
	return x
}

func main() {
	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
}
