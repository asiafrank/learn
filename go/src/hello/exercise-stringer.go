package main

import "fmt"

// 练习：Stringer
// 通过让 IPAddr 类型实现 fmt.Stringer 来打印点号分隔的地址。
// 例如，IPAddr{1, 2, 3, 4} 应当打印为 "1.2.3.4"。

type IPAddr [4]byte

func (ip IPAddr) String() string {
	return fmt.Sprintf("%v.%v.%v.%v", ip[0], ip[1], ip[2], ip[3])
}

func main() {
	hosts := map[string]IPAddr{
		"loopback":  {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}
