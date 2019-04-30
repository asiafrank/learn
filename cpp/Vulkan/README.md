## 注意事项
MacOS平台，在运行之前需要配置以下环境变量（可以放入 .bash_profile）
```shell
export VULKAN_SDK=path-to-sdk/macOS
export VK_ICD_FILENAMES=$VULKAN_SDK/etc/vulkan/icd.d/MoltenVK_icd.json
export VK_INSTANCE_LAYERS=VK_LAYER_LUNARG_api_dump:VK_LAYER_KHRONOS_validation
export VK_LAYER_PATH=$VULKAN_SDK/etc/vulkan/explicit_layer.d
export LD_LIBRARY_PATH=$VULKAN_SDK/lib:$VULKAN_SDK/lib/vulkan/layers
export VK_INSTANCE_LAYERS=VK_LAYER_LUNARG_api_dump:VK_LAYER_KHRONOS_validation
```