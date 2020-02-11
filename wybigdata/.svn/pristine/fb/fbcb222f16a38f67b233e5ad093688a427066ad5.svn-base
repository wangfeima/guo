package common.utils.json.r1103;

import common.utils.json.common.FirstParse;
import common.pojo.r1103.*;
import common.pojo.r1103.NothingBean.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class R1103Parse extends FirstParse {
    public static List<R1103Data> getR1103Object(String dataStr) {
        //第一、定义的是R1103对象中成员变量类型是List<R227Data>的成员变量r1103DataList
        List<R1103Data> r1103R1103DataList = new ArrayList<>();
        //第二、定义的是R1103对象中成员变量类型是List<R227Data>的成员变量r1103DataList的元素对象r1103Data
        R1103Data r1103R1103Data = new R1103Data();
        //第三、定义的是R1103对象中成员变量类型是List<R227Data>的成员变量r1103DataList的元素对象r1103Data中的各个数组成员变量
        List<Alter> alterList = new ArrayList<>();
        List<Basic> basicList = new ArrayList<>();
        List<CaseInfo> caseInfoList = new ArrayList<>();
        List<EntinvItem> entinvItemList = new ArrayList<>();
        List<Filiation> filiationList = new ArrayList<>();
        List<FrPosition> frPositionList = new ArrayList<>();
        List<Frinv> frinvList = new ArrayList<>();
        List<Liquidation> liquidationList = new ArrayList<>();
        List<MorDetail> morDetailList = new ArrayList<>();
        List<MorguaInfo> morguaInfoList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
        List<ShareHolder> shareHolderList = new ArrayList<>();
        List<SharesFrost> sharesFrostList = new ArrayList<>();
        List<SharesImpawn> sharesImpawnList = new ArrayList<>();

        //获取R1103-data中的JSON字符串中的JSON对象数组
        JSONArray r1103DataJsonObjectArray = JSON.parseArray(dataStr);

        //对R1103Json中Data数据中的Json数组数据的遍历
        for (Object r1103DataObject : r1103DataJsonObjectArray) {
            //对R1103Json中Data数据中的Json数组数据的元素进行对象化处理
            JSONObject r1103DataJsonObject = JSON.parseObject(r1103DataObject.toString());


            //获取personList对象
            String personListJsonStr = r1103DataJsonObject.get("personList").toString();
            //将personListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray personListJsonObjectArray = JSON.parseArray(personListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            personList.clear();
            for (Object personListObject : personListJsonObjectArray) {
                Person person = JSON.parseObject(personListObject.toString(), Person.class);
                personList.add(person);
            }
            r1103R1103Data.setPersonList(personList);


            //获取alterList对象
            String alterListJsonStr = r1103DataJsonObject.get("alterList").toString();
            //将alterListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray alterListJsonObjectArray = JSON.parseArray(alterListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            alterList.clear();
            for (Object alterListObject : alterListJsonObjectArray) {
                Alter alter = JSON.parseObject(alterListObject.toString(), Alter.class);
                alterList.add(alter);
            }
            r1103R1103Data.setAlterList(alterList);

            //获取basicList对象
            String basicListJsonStr = r1103DataJsonObject.get("basicList").toString();
            //将basicListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray basicListJsonObjectArray = JSON.parseArray(basicListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            basicList.clear();
            for (Object basicListObject : basicListJsonObjectArray) {
                Basic basic = JSON.parseObject(basicListObject.toString(), Basic.class);
                basicList.add(basic);
            }
            r1103R1103Data.setBasicList(basicList);


            //获取frPositionList对象
            String frPositionListJsonStr = r1103DataJsonObject.get("frPositionList").toString();
            //将frPositionListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray frPositionListJsonObjectArray = JSON.parseArray(frPositionListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            frPositionList.clear();
            for (Object frPositionListObject : frPositionListJsonObjectArray) {
                FrPosition frPosition = JSON.parseObject(frPositionListObject.toString(), FrPosition.class);
                frPositionList.add(frPosition);
            }
            r1103R1103Data.setFrPositionList(frPositionList);

            //获取frinvList对象
            String frinvListJsonStr = r1103DataJsonObject.get("frinvList").toString();
            //将frinvListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray frinvListJsonObjectArray = JSON.parseArray(frinvListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            frinvList.clear();
            for (Object frinvListObject : frinvListJsonObjectArray) {
                Frinv frinv = JSON.parseObject(frinvListObject.toString(), Frinv.class);
                frinvList.add(frinv);
            }
            r1103R1103Data.setFrinvList(frinvList);

            //获取morDetailList对象
            String morDetailListJsonStr = r1103DataJsonObject.get("morDetailList").toString();
            //将morDetailListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray morDetailListJsonObjectArray = JSON.parseArray(morDetailListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            morDetailList.clear();
            for (Object morDetailListObject : morDetailListJsonObjectArray) {
                MorDetail morDetail = JSON.parseObject(morDetailListObject.toString(), MorDetail.class);
                morDetailList.add(morDetail);
            }
            r1103R1103Data.setMorDetailList(morDetailList);

            //获取shareHolderList对象
            String shareHolderListJsonStr = r1103DataJsonObject.get("shareHolderList").toString();
            //将shareHolderListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray shareHolderListJsonObjectArray = JSON.parseArray(shareHolderListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            shareHolderList.clear();
            for (Object shareHolderListObject : shareHolderListJsonObjectArray) {
                ShareHolder shareHolder = JSON.parseObject(shareHolderListObject.toString(), ShareHolder.class);
                shareHolderList.add(shareHolder);
            }
            r1103R1103Data.setShareHolderList(shareHolderList);

            //获取sharesFrostList对象
            String sharesFrostListJsonStr = r1103DataJsonObject.get("sharesFrostList").toString();
            //将sharesFrostListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray sharesFrostListJsonObjectArray = JSON.parseArray(sharesFrostListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            sharesFrostList.clear();
            for (Object sharesFrostListObject : sharesFrostListJsonObjectArray) {
                SharesFrost sharesFrost = JSON.parseObject(sharesFrostListObject.toString(), SharesFrost.class);
                sharesFrostList.add(sharesFrost);
            }
            r1103R1103Data.setSharesFrostList(sharesFrostList);

            r1103R1103DataList.add(r1103R1103Data);
        }

        return r1103R1103DataList;
    }

}
