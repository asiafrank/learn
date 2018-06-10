# Introduction To 3D Game Programming with DirectX12 笔记

> 书中 frame,frame of reference,space,coordinate system 都是指坐标系

## 1.向量代数(Vector Algebra)
Direct3D 使用的是左手坐标系，OpenGL 使用右手坐标系。如图：  
![左右手坐标系](img/3d-directx12-f1d5.png)

向量基本操作，令![u=(u_{x},u_{y},u_{z}),v=(v_{x},v_{y},v_{z})](img/3d-directx12-latex-1.png "向量u,v")
- 如果 **u = v**, 则![向量u,v相等](img/3d-directx12-latex-2.png)
- ![向量u,v相加](img/3d-directx12-latex-3.png)
- ![向量u,v乘以一个系数](img/3d-directx12-latex-4.png)
- ![向量u,v相减](img/3d-directx12-latex-5.png)
 
长度与单位向量（Length And Unit Vectors）
- 令![u=(x,y,z)](img/3d-directx12-latex-1d7.png),长度公式：![\left \| u \right \|=\sqrt{x^{2}+y^{2}+z^{2}}](img/3d-directx12-latex-1d6.png)
- 单位向量 ![\hat{u}=\frac{u}{\left \| u \right \|}=(\frac{x}{\left \| u \right \|},\frac{y}{\left \| u \right \|},\frac{z}{\left \| u \right \|})](img/3d-directx12-latex-1d8.png)
- 单位向量长度 ![\left \| \hat{u} \right \|=\sqrt{(\frac{x}{\left \| u \right \|})^{2}+(\frac{y}{\left \| u \right \|})^{2}+(\frac{z}{\left \| u \right \|})^{2}}=\frac{\sqrt{x^{2}+y^{2}+z^{2}}}{\sqrt{\left \| u \right \|^{2}}}=\frac{\left \| u \right \|}{\left \| u \right \|}=1](img/3d-directx12-latex-1d9.png)


点乘(点积 The Dot Product)，令![u=(u_{x},u_{y},u_{z})](img/3d-directx12-latex-1d10.png),![v=(v_{x},v_{y},v_{z})](img/3d-directx12-latex-1d11.png)  
点乘公式1.3(代数定义)：  
![u \cdot v=u_{x}v_{x}+u_{y}v_{y}+u_{z}v_{z}](img/3d-directx12-latex-1d12.png)  
点乘的几何意义(几何定义)：一个向量在另外一个向量上的投影。有公式1.4：
![u \cdot v=\left \| u \right \|\left \| v \right \|cos\Theta](img/3d-directx12-latex-1d13.png)  
并且该公式表现出以下性质：
- 如果![u \cdot v=0](img/3d-directx12-latex-1d14.png)，则 ![u \perp v](img/3d-directx12-latex-1d15.png)，即 u 和 v 正交(orthogonal)
- 如果![u \cdot v>0](img/3d-directx12-latex-1d16.png)，则两个向量之间夹角小于90度(锐角)。
- 如果![u \cdot v<0](img/3d-directx12-latex-1d17.png)，则两个向量之间夹角大于90度(钝角)。
*几何定义与代数定义互推见 [wiki](https://zh.wikipedia.org/wiki/%E7%82%B9%E7%A7%AF)*


正交(Orthogonalization)  
有两个向量 v 和 n，则 v 在 n 上的正交[投影](https://zh.wikipedia.org/wiki/%E6%8A%95%E5%BD%B1)公式如下：  
![p=proj_{n}(v)=\left ( v \cdot \frac{n}{\left \| n \right \|} \right )\frac{n}{\left \| n \right \|}=\frac{(v \cdot n)}{\left \| n \right \|^{2}}n](img/3d-directx12-latex-1d18.png)

[Gram-Schmidt正交化](https://zh.wikipedia.org/wiki/%E6%A0%BC%E6%8B%89%E5%A7%86-%E6%96%BD%E5%AF%86%E7%89%B9%E6%AD%A3%E4%BA%A4%E5%8C%96)  
对于向量集 ![\left \{ v_{0},...,v_{n-1} \right (img/3d-directx12-latex-1d21.png)处理过程如下：  
令 ![w_{0}=v_{0}](img/3d-directx12-latex-1d19.png)  
当 ![1\leq i \leq n](img/3d-directx12-latex-1d20.png)，令 ![w_{i}=v_{i}-\sum_{j=0}^{i-1}proj_{w_{j}}(v_{j})](img/3d-directx12-latex-1d22.png)  
规范化，![w_{i}=\frac{w_{i}}{\left \| w_{i} \right \|}](img/3d-directx12-latex-1d23.png)

叉乘(叉积 The Cross Product)，它与点乘不同，点乘的计算结果是一个标量，而叉乘的计算结果是一个向量；另外，叉乘只能用于3D向量（2D向量没有叉乘）。  
令 ![u=(u_{x},u_{y},u_{z}),v=(v_{x},v_{y},v_{z})](img/3d-directx12-latex-1.png)，叉乘公式1.5：  
![w=u \times v=(u_{y}v_{z}-u_{z}v_{y},u_{z}v_{x}-u_{x}v_{z},u_{x}v_{y}-u_{y}v_{x})](img/3d-directx12-latex-1d24.png)  
叉乘有如下性质：
- 叉乘不满足交换律，![u \times v\neq v \times u](img/3d-directx12-latex-1d25.png)，实际上 ![u \times v = - v \times u](img/3d-directx12-latex-1d26.png)
- w 正交于 u,并且 w 正交于 v，即![w \cdot u=0](img/3d-directx12-latex-1d27.png)![w \cdot v=0](img/3d-directx12-latex-1d28.png)

2D伪叉乘(Psudo 2D Cross Product)，即求出一个2D向量 ![u=(u_{x},u_{y})](img/3d-directx12-latex-1d29.png) 的垂直向量 v。针对这个问题，![v=(-u_{y},u_{x})](img/3d-directx12-latex-1d30.png) 就满足条件了，此时![u \cdot v=0](img/3d-directx12-latex-1d14.png)。  

使用叉乘进行正交化如图1.15:  
- 1. 令![w_{0}=\frac{v_{0}}{\left \| v_{0} \right \|}](img/3d-directx12-latex-1d31.png)
- 2. 令![w_{2}=\frac{w_{0} \times v_{1}}{\left \| w_{0} \times v_{1} \right \|}](img/3d-directx12-latex-1d32.png)
- 3. 令![w_{1}=w_{2}\times w_{0}](img/3d-directx12-latex-1d33.png)。由后面的练习14可知，因为w2⊥w0且||w2||=||w0||=1，所以||w2×w0||=1，这样我们就无需进行规范化操作了。

![叉乘正交化](img/3d-directx12-f1d15.png "图1.15")  

点(Points)  
