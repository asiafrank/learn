package com.asiafrank.microservice.consumer;

import com.asiafrank.microservice.provider.api.HelloService;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * spring boot 示例
 *
 * @author zhangxiaofan 2019/04/17-19:21
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService helloService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Hello 示例
     * @param name 用户名称
     * @return "Hello {name}"
     */
    @ApiOperation(value = "Hello World")
    @GetMapping
    public HttpEntity<String> hello(String name) {
        if (Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("name is null");

        log.debug("i18n: {}", messageSource.getMessage("greeting", new Object[]{name}, Locale.getDefault()));
        String rs = helloService.hello(name);
        return ResponseEntity.ok(rs);
    }

    /*
    下载文件的 ResponseEntity 例子

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException {

        // ...

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentLength(file.length())
                             .contentType(MediaType.parseMediaType("application/octet-stream"))
                             .body(resource);
    }
    */
}
