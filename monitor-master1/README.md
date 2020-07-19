#  监测效能后端

## 数据库说明

### 数据库

```shell
JC_BASICINFO     # 基础信息库
JC_EQINFO        # 地震信息库
JC_HISTORYINFO   # 历史地震库
JC_MANAGEGRADE   # 监测运行管理得分库
```

### 数据表

#### JC_BASICINFO#基础信息库

```shell
BI_ANNUAL_REPORT           # 年报表
BI_ITEMKEY                 # 关键字匹配表
BI_PROVINCENAME            # 省份代码表
BI_SUB_FLUID               # 流体基础信息表
BI_USERS                   # 用户管理表
BI_SUB_GEOMAGNETISM        # 地磁数据表
BI_SUB_GEOELECTRIC         # 地电场数据表
BI_SUB_EARTHRESISTIVITY    # 地电阻率
BI_SUB_DISTORTION          # 形变前兆基础信息表
BI_DBM_TABLES              # 表管理表
BI_DBM_FIELDS              # 字段管理表
BI_MONTHLY_REPORT          # 月报表
```

#### JC_EQINFO#地震信息库

```shell
EI_ABNORMAL                # 异常表
EI_ITEMS                   # 主测项
EI_SEQUTYPEINFO            # 序列类型信息表
EI_PRECURSORYANOMALY_CHECK # 前兆异常信息待审核表
EI_PRECURSORYANOMALY       # 前兆异常信息表
EI_INTENSITYINFO           # 烈度信息表
EI_BASICINFO               # 地震基本信息表
```

#### JC_HISTORYINFO#历史地震库

```shell
HI_HISTORYEQ               # 历史地震表
```

#### JC_MANAGEGRADE#监测运行管理得分库

```shell
MG_SCOREMANAGER_DDZ        # 地电阻分数设置管理表
MG_SCOREMANAGER_DDC        # 地电场分数设置管理表
MG_DATA_TRACE              # 数据追踪得分表
MG_CP_DC_FINALSCORE        # 地磁分数汇总表
MG_CP_DC_INSTRU            # 地磁仪器系统表
MG_CP_DC_OBSDISTURB        # 地磁观测干扰情况表
MG_CP_DC_OBSENVI           # 地磁台站观测环境表
MG_CP_DC_QUALITYEVALUATION # 地磁数据质量评估表
MG_CP_DC_STAOPERAMANAGE    # 地磁台站运行管理表
MG_CP_DC_SYSTEMSTATE       # 地磁系统运行状态表
MG_CP_DDC_EQ               # 地电场震例情况表
MG_CP_DDC_FINALSCORE       # 地电场分数汇总表
MG_CP_DDC_INSTRU           # 地电场仪器系统表
MG_INSTRUMRES_SYS          # 仪器系统表
MG_CP_DDC_OBSDISTURB       # 地电场观测干扰情况表
MG_CP_DDC_OBSENVI          # 地电场台站观测环境表
MG_CP_DDC_QUALITYEVALUATION# 地电场数据质量评估表
MG_CP_DDC_STAOPERAMA       # 地电场台站运行管理表
MG_CP_DDC_SYSSTATE         # 地电场系统运行状态表
MG_CP_DDZ_EQ               # 地电阻震例情况表
MG_CP_DDZ_FINALSCORE       # 地电阻分数汇总表
MG_CP_DDZ_INSTRU           # 地电阻仪器系统表
MG_CP_DDZ_OBSDISTURB       # 地电阻观测干扰情况表
MG_CP_DDZ_OBSENVI          # 地电阻台站观测环境表
MG_CP_DC_EQ                # 地磁震例情况表
MG_CP_DDZ_QUALITYEVALUATION# 地电阻数据质量评估表
MG_CP_DDZ_STAOPERAMANAGE   # 地电阻台站运行管理表
MG_CP_DDZ_SYSTEMSTATE      # 地电阻系统运行状态表
MG_CP_XB_EQ                # 形变震例情况表
MG_CP_XB_FINALSCORE        # 形变分数汇总表
MG_CP_XB_OBSDISTURB        # 形变观测干扰情况表
MG_CP_XB_OBSENVI           # 形变台站观测环境表
MG_CP_XB_POINTMAT          # 形变测点配套性表
MG_CP_XB_QUALITYEVALUATION # 形变数据质量评估表
MG_CP_XB_STAOPMANAGER      # 形变台站运行管理表
MG_STATION_STATUS          # 台站运行管理得分表
MG_CP_XB_STAOPSTATUS       # 形变台站运行状态表
MG_CP_LT_DATATRACAPP       # 流体数据跟踪应用表
MG_CP_LT_EQ                # 流体震例情况表
MG_CP_LT_FINALSCORE        # 流体分数汇总表
MG_CP_LT_INSSYSTCONDIT     # 流体仪器系统条件表
MG_CP_LT_OBSENVI           # 流体台站环境表
MG_CP_LT_QUALITYEVALUATION # 流体数据质量评估表
MG_CP_LT_STAOPERMANAGE     # 流体台站运行管理表
MG_CP_LT_SYSTEMSTATE       # 流体仪器系统表
MG_SCOREMANAGER_XB         # 形变分数设置管理表
```

## 文件目录

```shell
├── pom.xml                                                       # maven 依赖管理文件
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── gis
│   │   │           └── monitor
│   │   │               ├── MonitorApplication.java               # 项目总入口 
│   │   │               ├── config                                # 配置目录
│   │   │               │   ├── BasicInfoConfig.java              # 基础信息库配置
│   │   │               │   ├── JwtFilterConfig.java              # 拦截器配置
│   │   │               │   └── ManageGradeConfig.java            # 管理得分库设置
│   │   │               ├── controller                            # 控制器目录
│   │   │               │   ├── AuthController.java								# 验证控制器，负责用户的登陆
│   │   │               │   ├── GlobalExceptionHandle.java        # 全局异常处理类，负责全局异常，统一返回JSON格式
│   │   │               │   ├── JwtInterceptor.java               # 拦截器实现，拦截并验证请求
│   │   │               │   └── whatController.java               # 测试用控制器
│   │   │               ├── error                                 # 异常目录
│   │   │               │   ├── AuthException.java                # 权限异常类
│   │   │               │   ├── BaseException.java                # 基本异常类，其他异常需继承此类
│   │   │               │   └── UserException.java                # 用户异常类
│   │   │               ├── mapper                                # mapper 数据层，用于数据库操作
│   │   │               │   ├── basicinfo                         # 基础信息库相关数据库操作
│   │   │               │   │   └── BI_USERS_MAPPER.java          # 用户表相关数据库操作
│   │   │               │   └── managegrade                       # 管理得分库相关数据库操作
│   │   │               │       └── MG_CP_DC_EQ_MAPPER.java       # 地磁震例情况表相关数据库操作
│   │   │               ├── pojo                                  # 简单的Java对象，表实例，相当于bean
│   │   │               │   ├── basicinfo 												# 基础信息库
│   │   │               │   │   └── BI_USERS.java									
│   │   │               │   └── managegrade                       # 管理得分库
│   │   │               │       └── MG_CP_DC_EQ.java
│   │   │               ├── service                               # Sevice 层
│   │   │               │   └── AuthService.java                  # 验证相关操作
│   │   │               └── utils                                 # 通用工具
│   │   │                   ├── JsonResult.java                   # 数据统一封装返回
│   │   │                   ├── JwtToken.java                     # token 对象类
│   │   │                   └── JwtUtil.java                      # token 相关操作类
│   │   └── resources                                             # 资源目录
│   │       ├── application.yaml                                  # springboot 相关配置
│   │       ├── error_codes.properties                            # 错误信息
│   │       ├── mybatis                                           # mybatis 相关配置
│   │       │   └── mapper                                        # 与上面的mappe对应，里面存放具体sql
│   │       │       ├── basicinfo
│   │       │       │   └── BI_USERS_MAPPER.xml                    
│   │       │       └── managegrade
│   │       │           └── MG_CP_DC_EQ_MAPPER.xml                
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── gis
│                   └── monitor
│                       └── MonitorApplicationTests.java
└── target
```

