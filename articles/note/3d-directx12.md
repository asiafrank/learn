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

---
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

---
正交(Orthogonalization)  
有两个向量 v 和 n，则 v 在 n 上的正交[投影](https://zh.wikipedia.org/wiki/%E6%8A%95%E5%BD%B1)公式如下：  
![p=proj_{n}(v)=\left ( v \cdot \frac{n}{\left \| n \right \|} \right )\frac{n}{\left \| n \right \|}=\frac{(v \cdot n)}{\left \| n \right \|^{2}}n](img/3d-directx12-latex-1d18.png)

[Gram-Schmidt正交化](https://zh.wikipedia.org/wiki/%E6%A0%BC%E6%8B%89%E5%A7%86-%E6%96%BD%E5%AF%86%E7%89%B9%E6%AD%A3%E4%BA%A4%E5%8C%96)  
对于向量集 ![\left \{ v_{0},...,v_{n-1} \right](img/3d-directx12-latex-1d21.png)处理过程如下：  
令 ![w_{0}=v_{0}](img/3d-directx12-latex-1d19.png)  
当 ![1\leq i \leq n](img/3d-directx12-latex-1d20.png)，令 ![w_{i}=v_{i}-\sum_{j=0}^{i-1}proj_{w_{j}}(v_{j})](img/3d-directx12-latex-1d22.png)  
规范化，![w_{i}=\frac{w_{i}}{\left \| w_{i} \right \|}](img/3d-directx12-latex-1d23.png)

---
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

点(Points) ，由于使用向量可以很方便地表示相对于坐标系的点，所以我们不必为单独设计一套针对于点的运算，只需要借助于前面讨论过的向量代数框架就可以处理它们。

---
### DirectX 数学向量
DirectX 数学库，使用了[SSE2(Streaming SIMD Extensions 2)](https://en.wikipedia.org/wiki/SSE2)指令集。通过 128-bits 宽的[SIMD(single instruction multiple data)](https://en.wikipedia.org/wiki/SIMD)寄存器，能做到一个指令操作 4 个 32-bits 大小的float或int数据。

#### 向量类型
XMVECTOR，是DirectX Math 的核心向量类型，支持SIMD。XMFLOAT2(2D)、XMFLOAT3(3D)、XMFLOAT4(4D)类型，不支持 SIMD。
因此，推荐按如下情况使用：
- 1. 本地或全局变量，使用 XMVECTOR
- 2. class 成员，使用 XMFLOATn
- 3. 做数学计算前，使用加载函数（loading functions）将 XMFLOATn 转换成 XMVECTOR
- 4. 永远使用 XMVECTOR 做数学计算
- 5. 使用存储函数（storage functions）将 XMVECTOR 转换成 XMFLOATn

#### 加载和存储方法  
XMFLOATn -> XMVECTOR, 加载方法
```cpp
// Loads XMFLOAT2 into XMVECTOR
XMVECTOR XM_CALLCONV XMLoadFloat2(const XMFLOAT2 *pSource);

// Loads XMFLOAT3 into XMVECTOR
XMVECTOR XM_CALLCONV XMLoadFloat3(const XMFLOAT3 *pSource);

// Loads XMFLOAT4 into XMVECTOR
XMVECTOR XM_CALLCONV XMLoadFloat4(const XMFLOAT4 *pSource);
```
XMVECTOR -> XMFLOATn，存储方法
```cpp
// Loads XMVECTOR into XMFLOAT2
void XM_CALLCONV XMStoreFloat2(XMFLOAT2 *pDestination, FXMVECTOR V);

// Loads XMVECTOR into XMFLOAT3
void XM_CALLCONV XMStoreFloat3(XMFLOAT3 *pDestination, FXMVECTOR V);

// Loads XMVECTOR into XMFLOAT4
void XM_CALLCONV XMStoreFloat4(XMFLOAT4 *pDestination, FXMVECTOR V);
```
有时我们只想 get 或 set 某一个维度的值，可以使用下面方法：
```cpp
float XM_CALLCONV XMVectorGetX(FXMVECTOR V);
float XM_CALLCONV XMVectorGetY(FXMVECTOR V);
float XM_CALLCONV XMVectorGetZ(FXMVECTOR V);
float XM_CALLCONV XMVectorGetW(FXMVECTOR V);

XMVECTOR XM_CALLCONV XMVectorSetX(FXMVECTOR V, float x);
XMVECTOR XM_CALLCONV XMVectorSetY(FXMVECTOR V, float y);
XMVECTOR XM_CALLCONV XMVectorSetZ(FXMVECTOR V, float z);
XMVECTOR XM_CALLCONV XMVectorSetW(FXMVECTOR V, float w);
```
#### 参数传递
为了效率，XMVECTOR的值可以使用 SSE/SSE2 寄存器代替栈，将参数传递给方法。但是这种高效的方式所支持的参数数量取决于平台和编译器。所以为了保证平台无关性，我们需要使用 FXMVECTOR、GXMVECTOR、HXMVECTOR、CXMVECTOR 来传递 XMVECTOR 参数。这些类型能被平台和编译器替换成相应的类型。并且方法上需要写上 XM_CALLCONV，供不同版本的编译器使用不同的[调用约定(calling convention)](https://msdn.microsoft.com/en-us/library/windows/desktop/ee418728(v=vs.85).aspx)。

XMVECTOR 的参数传递的规则如下：
- 1. 前 3 个 XMVECTOR 参数使用 FXMVECTOR;
- 2. 第 4 个 XMVECTOR 参数使用 GXMEVECTOR;
- 3. 第 5，第 6 个 XMVECTOR 参数使用 HXMVECTOR;
- 4. 剩下的 XMVECTOR 参数使用 CXMVECTOR.

例：
```cpp
inline XMMATRIX XM_CALLCONV XMMatrixTransformation(
    FXMVECTOR ScalingOrigin,
    FXMVECTOR ScalingOrientationQuaternion,
    FXMVECTOR Scaling,
    GXMVECTOR RotationOrigin,
    HXMVECTOR RotationQuaternion,
    HXMVECTOR Translation);

inline XMMATRIX XM_CALLCONV XMMatrixTransformation2D(
    FXMVECTOR ScalingOrigin,
    float     ScalingOrientation,
    FXMVECTOR Scaling,
    FXMVECTOR RotationOrigin,
    float     Rotation,
    GXMVECTOR Translation);
```
上述规则仅只在“入参”(input parameters)时才会使用到 SSE/SSE2 寄存器，“出参”(output parameters，像 XMVECTOR& 或 XMVECTOR*，会将参数值转递给调用者)并不会有这样的特性。

#### 常向量(Constant Vectors)
作为常量的 XMVECTOR 实例，应使用 XMVECTORF32 类型。例：
```cpp
static const XMVECTORF32 g_vHalfVector = { 0.5f, 0.5f, 0.5f, 0.5f };
static const XMVECTORF32 g_vZero       = { 0.0f, 0.0f, 0.0f, 0.0f };
XMVECTORF32 vRightTop = {
    vViewFrust.RightSlope,
    vViewFrust.TopSlope,
    1.0f,1.0f
};
XMVECTORF32 vLeftBottom = {
    vViewFrust.LeftSlope,
    vViewFrust.BottomSlope,
    1.0f,1.0f
};
```
基本上，我们只要想使用初始化语法就可以使用XMVECTORF32。XMVECTORF32 是一个16-bits参数对齐(aligned)的结构(structure)。  
我们也可以使用 XMVECTORU32 来构造整数型的 XMVECTOR:
```cpp
static const XMVECTORU32 vGrabY = {
    0x00000000,0xFFFFFFFF,0x00000000,0x00000000
};
```

#### 重载操作符(Overloaded Operators)
XMVECTOR 可以使用加减乘除操作符，定义如下：
```cpp
XMVECTOR  XM_CALLCONV operator+ (FXMVECTOR V);
XMVECTOR  XM_CALLCONV operator- (FXMVECTOR V);
XMVECTOR& XM_CALLCONV operator+= (XMVECTOR& V1, FXMVECTOR V2);
XMVECTOR& XM_CALLCONV operator-= (XMVECTOR& V1, FXMVECTOR V2);
XMVECTOR& XM_CALLCONV operator*= (XMVECTOR& V1, FXMVECTOR V2);
XMVECTOR& XM_CALLCONV operator/= (XMVECTOR& V1, FXMVECTOR V2);
XMVECTOR& operator*= (XMVECTOR& V, float S);
XMVECTOR& operator/= (XMVECTOR& V, float S);
XMVECTOR XM_CALLCONV operator+ (FXMVECTOR V1, FXMVECTOR V2);
XMVECTOR XM_CALLCONV operator- (FXMVECTOR V1, FXMVECTOR V2);
XMVECTOR XM_CALLCONV operator* (FXMVECTOR V1, FXMVECTOR V2);
XMVECTOR XM_CALLCONV operator/ (FXMVECTOR V1, FXMVECTOR V2);
XMVECTOR XM_CALLCONV operator* (FXMVECTOR V, float S);
XMVECTOR XM_CALLCONV operator* (float S, FXMVECTOR V);
XMVECTOR XM_CALLCONV operator/ (FXMVECTOR V, float S);
```

#### 杂项
DirectX Math 库还定义了一些有关 π 的常量:
```cpp
const float XM_PI      = 3.141592654f;
const float XM_2PI     = 6.283185307f;
const float XM_1DIVPI  = 0.318309886f;
const float XM_1DIV2PI = 0.159154943f;
const float XM_PIDIV2  = 1.570796327f;
const float XM_PIDIV4  = 0.785398163f;
```
另外定义了长度和角度的内联函数:
```cpp
inline float XMConvertToRadians(float fDegrees)
{ return fDegrees * (XM_PI / 180.0f); }
inline float XMConvertToDegrees(float fRadians)
{ return fRadians * (180.0f / XM_PI); }
```
还有 min/max 函数:
```cpp
template<class T> inline T XMMin(T a, T b) 
{ return (a < b) ? a : b; }
template<class T> inline T XMMax(T a, T b) 
{ return (a > b) ? a : b; }
```

#### Setter 函数
```cpp
// Returns the zero vector 0
XMVECTOR XM_CALLCONV XMVectorZero();
// Returns the vector (1, 1, 1, 1)
XMVECTOR XM_CALLCONV XMVectorSplatOne();
// Returns the vector (x, y, z, w)
XMVECTOR XM_CALLCONV XMVectorSet(float x, float y, float z, float w);
// Returns the vector (s, s, s, s)
XMVECTOR XM_CALLCONV XMVectorReplicate(float Value);
// Returns the vector (v x, v x, v x, v x )
XMVECTOR XM_CALLCONV XMVectorSplatX(FXMVECTOR V);
// Returns the vector (v y, v y, v y, v y )
XMVECTOR XM_CALLCONV XMVectorSplatY(FXMVECTOR V);
// Returns the vector (v z, v z, v z, v z )
XMVECTOR XM_CALLCONV XMVectorSplatZ(FXMVECTOR V);
```

#### 向量函数
```cpp
// Returns ||v||
XMVECTOR XM_CALLCONV XMVector3Length(FXMVECTOR V); // Input v
// Returns ||v||^2
XMVECTOR XM_CALLCONV XMVector3LengthSq(FXMVECTOR V); // Input v
 // Returns v1·v2
XMVECTOR XM_CALLCONV XMVector3Dot(
    FXMVECTOR V1,  // Input v1
    FXMVECTOR V2); // Input v2
// Returns v1×v2
XMVECTOR XM_CALLCONV XMVector3Cross( 
    FXMVECTOR V1,  // Input v1
    FXMVECTOR V2); // Input v2
// Returns v/||v||
XMVECTOR XM_CALLCONV XMVector3Normalize(FXMVECTOR V); // Input v
// Returns a vector orthogonal to v
XMVECTOR XM_CALLCONV XMVector3Orthogonal(FXMVECTOR V); // Input v
// Returns the angle between v 1 and v 2
XMVECTOR XM_CALLCONV XMVector3AngleBetweenVectors( 
    FXMVECTOR V1,  // Input v1
    FXMVECTOR V2); // Input v2
void XM_CALLCONV XMVector3ComponentsFromNormal(
    XMVECTOR* pParallel,      // Returns projn (v)
    XMVECTOR* pPerpendicular, // Returns perpn (v)
    FXMVECTOR V,       // Input v
    FXMVECTOR Normal); // Input n
// Returns v1=v2
bool XM_CALLCONV XMVector3Equal(
    FXMVECTOR V1,  // Input v1
    FXMVECTOR V2); // Input v2
// Returns v 1 ≠ v 2
bool XM_CALLCONV XMVector3NotEqual(
    FXMVECTOR V1,  // Input v1
    FXMVECTOR V2); // Input v2
```
#### Floating-Point 错误
浮点数在计算机系统中都是近似表达，因此针对任意实数 p，1的p次方=1，该等式在计算机系统中不成立。  
为了避免这种不精确的情况，我们定义了一个 epsilon 变量作为误差值，当两个值相差小于 epsilon，则就将这两个值视作相等（即近似相等）。函数定义如下：
```cpp
const float Epsilon = 0.001f;
bool Equals(float lhs, float rhs)
{
    // Is the distance between lhs and rhs less than EPSILON?
    return fabs(lhs - rhs) < Epsilon ? true : false;
}
```
DirectX Math 库中已有类似的函数 XMVector3NearEqual:
```cpp
// Returns
// abs(U.x – V.x) <= Epsilon.x &&
// abs(U.y – V.y) <= Epsilon.y &&
// abs(U.z – V.z) <= Epsilon.z
XMFINLINE bool XM_CALLCONV XMVector3NearEqual(
    FXMVECTOR U,
    FXMVECTOR V,
    FXMVECTOR Epsilon);
```

## 2 矩阵代数
