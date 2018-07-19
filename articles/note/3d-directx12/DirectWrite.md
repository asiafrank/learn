# DirectWrite 笔记
该笔记根据[官方文档](https://docs.microsoft.com/en-us/windows/desktop/directwrite/direct-write-portal)所作。

学这个的目的是为了能在 Direct3D 程序中显示中文。

## 常用接口整理
- [IDWriteFactory](https://msdn.microsoft.com/en-us/library/Dd368183(v=VS.85).aspx)：所有的 DirectWrite 对象都由该工厂类创建。
- [IDWriteTextFormat](https://msdn.microsoft.com/en-us/library/Dd316628(v=VS.85).aspx)：代表字体和段落属性，用于将文字格式化。
- [IDWriteTextLayout](https://msdn.microsoft.com/en-us/library/Dd316718(v=VS.85).aspx)：该接口继承 IDWriteTextFormat。用于代表已经被解析和格式化的文字块，即文字布局。调用[IDWriteFactory::CreateTextLayout](https://msdn.microsoft.com/en-us/library/Dd368205(v=VS.85).aspx)方法，输入字符串和IDWriteTextFormat 对象等返回结果是 IDWriteTextLayout 对象
- [ID2D1RenderTarget::DrawTextLayout](https://msdn.microsoft.com/library/windows/desktop/dd371913)：用于渲染文字
- [IDWriteFontCollection](https://msdn.microsoft.com/en-us/library/Dd368214(v=VS.85).aspx)：该接口封装了访问系统字体的方法，如 [GetFontFamily](https://msdn.microsoft.com/en-us/library/Dd371143(v=VS.85).aspx)能获取特定的 FontFamily 对象，然后调用[IDWriteFontFamily::GetFirstMatchingFont](https://msdn.microsoft.com/en-us/library/Dd371051(v=VS.85).aspx)获取 [IDWriteFont](https://msdn.microsoft.com/en-us/library/Dd368213(v=VS.85).aspx)，该对象代表字体对象，能从中获取字体的属性。
- [IDWriteFontFace](https://msdn.microsoft.com/en-us/library/Dd370983(v=VS.85).aspx)：比 IDWriteFont 更完整的字体对象。下面是 IDWriteFont 和 IDWriteFontFace 的应用场景区分。

|场景| IDWriteFont| IDWriteFonFace|
|:-------------|:---|:---|
|用于支持用户交互的API，例如字体选择器用户界面：描述和其他信息API|Yes|No|
|用于支持字体映射的API：族，样式，权重，拉伸，字符覆盖|Yes|No|
|DrawText API|Yes|No|
|用于渲染的API|No|Yes|
|用于文本布局的API：字形指标等|No|Yes|
|用于UI控件和文本布局的API：字体范围的指标|Yes|Yes|

- [IDWriteTextRenderer](https://msdn.microsoft.com/en-us/library/Dd371523(v=VS.85).aspx)：表示一组应用程序定义的回调，用于执行文本，内联对象和装饰（如下划线）的呈现。
- [IDWriteTextRenderer::DrawGlyphRun](https://msdn.microsoft.com/en-us/library/Dd371526(v=VS.85).aspx)：该方法被 IDWriteTextLayout::Draw 调用，用于渲染文字。该方法有两种实现 [ID2DRenderTarget::DrawGlyphRun](https://msdn.microsoft.com/library/windows/desktop/dd371893) 使用 GPU 做渲染，[IDWriteBitmapRenderTarget::DrawGlyphRun](https://msdn.microsoft.com/en-us/library/Dd368167(v=VS.85).aspx) 使用位图做渲染

## 如何在 Direct3D 12 中显示文字
下面文章描述了 2D 与 Direct3D 相互整合的方案。
- [Direct3D 12 interop](https://docs.microsoft.com/en-us/windows/desktop/direct3d12/direct3d-12-with-direct3d-11--direct-2d-and-gdi)
- [D2D using D3D11on12](https://docs.microsoft.com/en-us/windows/desktop/direct3d12/d2d-using-d3d11on12)
- [代码参考](https://github.com/Microsoft/DirectX-Graphics-Samples/tree/master/Samples/Desktop/D3D1211On12)

## 如何在 Direct3D 12 中播放视频
和显示文字一样，需要 Direct3D 11 和 Direct3D 12 整合
- [Direct3D 11 Video APIs](https://docs.microsoft.com/en-us/windows/desktop/medfound/direct3d-11-video-apis)