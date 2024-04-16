package com.gy.springbootinit.manager;

import cn.hutool.core.text.StrSplitter;
import com.gy.springbootinit.common.ErrorCode;
import com.gy.springbootinit.exception.BusinessException;
import com.gy.springbootinit.exception.ThrowUtils;
import com.gy.springbootinit.model.entity.Chart;
import com.gy.springbootinit.model.vo.BiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiManagerTest {

    @Resource
    private AiManager aiManager;

    @Test
    void doChat() {
        String answer = aiManager.doChat(1778753504153083905L, "分析需求：\n" +
                "分析网站用户的增长情况\n" +
                "原始数据：\n" +
                "日期,用户数\n" +
                "1号,10\n" +
                "2号,20\n" +
                "3号,30\n");
        System.out.println(answer);
    }

    @Test
    void doChat2(){
        String result="【【【【【\n" +
                "{\n" +
                "  \"tooltip\": {\n" +
                "    \"trigger\": \"axis\",\n" +
                "    \"axisPointer\": {\n" +
                "      \"type\": \"shadow\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"xAxis\": {\n" +
                "    \"type\": \"category\",\n" +
                "    \"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\"]\n" +
                "  },\n" +
                "  \"yAxis\": {\n" +
                "    \"type\": \"value\"\n" +
                "  },\n" +
                "  \"series\": [\n" +
                "    {\n" +
                "      \"name\": \"用户数\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"data\": [10, 20, 30, 40, 50]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "【【【【【\n" +
                "根据提供的数据，我们可以得出以下结论：\n" +
                "\n" +
                "- 用户数从1号到5号逐渐增加，呈现出一个线性增长的态势。\n" +
                "- 1号最少，有10个用户；5号最多，有50个用户。\n" +
                "- 每天用户数的增加量都是10个，显示出稳定的增长趋势。\n" +
                "- 这可能意味着某种推广活动或者自然增长趋势在起作用，导致用户数的逐日增加。需要进一步的数据或背景信息来确认增长的具体原因。";
        System.out.println("result:"+result);
        // 对返回结果做拆分,按照5个中括号进行拆分
        String[] splits = result.split("【【【【【");
        // 拆分之后还要进行校验
        if (splits.length < 3) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }

        String genChart = splits[1].trim();
        String genResult = splits[2].trim();
        System.out.println("genChart:"+"\n"+genChart);
        System.out.println("genResult:"+"\n"+genResult);

        BiResponse biResponse = new BiResponse();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        //biResponse.setChartId(chart.getId());
    }

    @Test
    void doChat3(){
        String result="【【【【【\n" +
                "{\n" +
                "  \"tooltip\": {\n" +
                "    \"trigger\": \"axis\",\n" +
                "    \"axisPointer\": {\n" +
                "      \"type\": \"shadow\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"xAxis\": {\n" +
                "    \"type\": \"category\",\n" +
                "    \"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\"]\n" +
                "  },\n" +
                "  \"yAxis\": {\n" +
                "    \"type\": \"value\"\n" +
                "  },\n" +
                "  \"series\": [\n" +
                "    {\n" +
                "      \"name\": \"用户数\",\n" +
                "      \"type\": \"bar\",\n" +
                "      \"data\": [10, 20, 30, 40, 50]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "【【【【【\n" +
                "根据提供的数据，我们可以得出以下结论：\n" +
                "\n" +
                "- 用户数从1号到5号逐渐增加，呈现出一个线性增长的态势。\n" +
                "- 1号最少，有10个用户；5号最多，有50个用户。\n" +
                "- 每天用户数的增加量都是10个，显示出稳定的增长趋势。\n" +
                "- 这可能意味着某种推广活动或者自然增长趋势在起作用，导致用户数的逐日增加。需要进一步的数据或背景信息来确认增长的具体原因。";
        List<String> split = StrSplitter.split(result, "【【【【【", 0, true, true);
        if (split.size() < 2) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }
        String genChart = split.get(0).trim();
        String genResult = split.get(1).trim();
        System.out.println("genChart"+genChart);
        System.out.println("genResult"+genResult);
    }



}