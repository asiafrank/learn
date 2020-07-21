package main

import "fmt"

// 练习：Web 爬虫
// 在这个练习中，我们将会使用 Go 的并发特性来并行化一个 Web 爬虫。
// 修改 Crawl 函数来并行地抓取 URL，并且保证不重复。
// 提示：你可以用一个 map 来缓存已经获取的 URL，但是要注意 map 本身并不是并发安全的！

type Fetcher interface {
	// Fetch 返回 URL 的 body 内容，并且将在这个页面上找到的 URL 放到一个 slice 中。
	Fetch(url string) (body string, urls []string, err error)
}

type result struct {
	url, body string
	urls      []string
	err       error
	depth     int
}

// Crawl 使用 fetcher 从某个 URL 开始递归的爬取页面，直到达到最大深度。
func Crawl(url string, depth int, fetcher Fetcher) {
	results := make(chan *result)
	fetched := make(map[string]bool)
	fetch := func(url string, depth int) {
		body, urls, err := fetcher.Fetch(url)
		results <- &result{url, body, urls, err, depth}
	}

	go fetch(url, depth)
	fetched[url] = true

	// 1 url is currently being fetched in background, loop while fetching
	for fetching := 1; fetching > 0; fetching-- {
		res := <-results

		// skip failed fetches
		if res.err != nil {
			fmt.Println(res.err)
			continue
		}

		fmt.Printf("found: %s %q\n", res.url, res.body)

		// follow links if depth has not been exhausted
		if res.depth > 0 {
			for _, u := range res.urls {
				// don't attempt to re-fetch known url, decrement depth
				if !fetched[u] {
					fetching++
					go fetch(u, res.depth-1)
					fetched[u] = true
				}
			}
		}
	}

	close(results)
}

func main() {
	Crawl("https://golang.org/", 4, fetcher)
}

// fakeFetcher 是返回若干结果的 Fetcher。
type fakeFetcher map[string]*fakeResult

type fakeResult struct {
	body string
	urls []string
}

func (f fakeFetcher) Fetch(url string) (string, []string, error) {
	if res, ok := f[url]; ok {
		return res.body, res.urls, nil
	}
	return "", nil, fmt.Errorf("not found: %s", url)
}

// fetcher 是填充后的 fakeFetcher。
var fetcher = fakeFetcher{
	"https://golang.org/": &fakeResult{
		"The Go Programming Language",
		[]string{
			"https://golang.org/pkg/",
			"https://golang.org/cmd/",
		},
	},
	"https://golang.org/pkg/": &fakeResult{
		"Packages",
		[]string{
			"https://golang.org/",
			"https://golang.org/cmd/",
			"https://golang.org/pkg/fmt/",
			"https://golang.org/pkg/os/",
		},
	},
	"https://golang.org/pkg/fmt/": &fakeResult{
		"Package fmt",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
	"https://golang.org/pkg/os/": &fakeResult{
		"Package os",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
}
