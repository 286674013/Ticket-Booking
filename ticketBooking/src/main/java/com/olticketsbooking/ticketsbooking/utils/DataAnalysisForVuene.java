package com.olticketsbooking.ticketsbooking.utils;

public class DataAnalysisForVuene {
    //7日/14日/28日/100日/360日
//    总体收入情况---总销售额,总订单数,下单客户数,总收入额,总退款额,
//    订单实际情况---订单完成数/率,退单数/率,客户满意率,
//    演出实际情况---单个演出售票数排行,单个演出销售额排行,按类型演出售票数排行,按类型演出销售额排行,关键词票数及销售额排行____(演出相关排行取最多前十)
//    顾客占比情况---不同类型顾客数量/占比;不同类型顾客的下单数占比\消费总额占比;不同爱好类型的顾客的下单数占比\消费总额占比;
//    平台相对情况---市场占有率(场馆销售额平台占有率),平台票数排行,平台市场占有排行
 //所需数据:订单+peform+user_orderstate,paytime,performstart,countseats,performid,performname,performtype,performkeys,usertype,usertype,userpreference

    //1
    int dataAnalysisForVueneid;
    String dataAnalysisName;//vueneid-7/14/28 100/360
    int timeRange;//days 7日/14日/28日/100日/360日
    int vueneid;

    double countAllMoney;
    double countIncome;
    double countMoneyBack;
    int countAllOrder;
    int countAllCustomer;
    //2
    int countOrderFinished;
    double percentageOfOrderFinished;
    int countOrderCancled;
    double getPercentageOfOrderCancled;
    double satisfactionPoint;
    //3
    //演出前十排行列表展示,类型使用饼状图展示,关键词前十使用列表展示
    String[] nameListOfPerformTicketRank;
    int[] amountListOfPerformTicketRank;
    String[] nameListOfPerformMoneyRank;
    int[] amountListOfPerformMoneyRank;
    //饼状图
    String[] nameListOfPerformTypeTicketRank;
    int[] amountListOfPerformTypeTicketRank;
    String[] nameListOfPerformTypeMoneyRank;
    int[] amountListOfPerformTypeMoneyRank;
    //
    String[] nameListOfPerformKeysTicketRank;
    int[] amountListOfPerformKeysTicketRank;
    String[] nameListOfPerformKeysMoneyRank;
    int[] amountListOfPerformKeysMoneyRank;
    //4 0-6等共七种客户-该部分无需比例,饼状图自有
    int[] amountListOfCustomerType;
    int[] amountListOfCustomerTypeTicket;
    double[] amountListOfCustomerTypeMoney;
    //爱好统计取前十的爱好关键词,用户的关键词分爱好程度加权,在乘以票数(金额)
    String[] preferenceNameListForTicket;

    public DataAnalysisForVuene() {
    }

    public int getDataAnalysisForVueneid() {
        return dataAnalysisForVueneid;
    }

    public void setDataAnalysisForVueneid(int dataAnalysisForVueneid) {
        this.dataAnalysisForVueneid = dataAnalysisForVueneid;
    }

    public String getDataAnalysisName() {
        return dataAnalysisName;
    }

    public void setDataAnalysisName(String dataAnalysisName) {
        this.dataAnalysisName = dataAnalysisName;
    }

    public int getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(int timeRange) {
        this.timeRange = timeRange;
    }

    public int getVueneid() {
        return vueneid;
    }

    public void setVueneid(int vueneid) {
        this.vueneid = vueneid;
    }

    public double getCountAllMoney() {
        return countAllMoney;
    }

    public void setCountAllMoney(double countAllMoney) {
        this.countAllMoney = countAllMoney;
    }

    public double getCountIncome() {
        return countIncome;
    }

    public void setCountIncome(double countIncome) {
        this.countIncome = countIncome;
    }

    public double getCountMoneyBack() {
        return countMoneyBack;
    }

    public void setCountMoneyBack(double countMoneyBack) {
        this.countMoneyBack = countMoneyBack;
    }

    public int getCountAllOrder() {
        return countAllOrder;
    }

    public void setCountAllOrder(int countAllOrder) {
        this.countAllOrder = countAllOrder;
    }

    public int getCountAllCustomer() {
        return countAllCustomer;
    }

    public void setCountAllCustomer(int countAllCustomer) {
        this.countAllCustomer = countAllCustomer;
    }

    public int getCountOrderFinished() {
        return countOrderFinished;
    }

    public void setCountOrderFinished(int countOrderFinished) {
        this.countOrderFinished = countOrderFinished;
    }

    public double getPercentageOfOrderFinished() {
        return percentageOfOrderFinished;
    }

    public void setPercentageOfOrderFinished(double percentageOfOrderFinished) {
        this.percentageOfOrderFinished = percentageOfOrderFinished;
    }

    public int getCountOrderCancled() {
        return countOrderCancled;
    }

    public void setCountOrderCancled(int countOrderCancled) {
        this.countOrderCancled = countOrderCancled;
    }

    public double getGetPercentageOfOrderCancled() {
        return getPercentageOfOrderCancled;
    }

    public void setGetPercentageOfOrderCancled(double getPercentageOfOrderCancled) {
        this.getPercentageOfOrderCancled = getPercentageOfOrderCancled;
    }

    public double getSatisfactionPoint() {
        return satisfactionPoint;
    }

    public void setSatisfactionPoint(double satisfactionPoint) {
        this.satisfactionPoint = satisfactionPoint;
    }

    public String[] getNameListOfPerformTicketRank() {
        return nameListOfPerformTicketRank;
    }

    public void setNameListOfPerformTicketRank(String[] nameListOfPerformTicketRank) {
        this.nameListOfPerformTicketRank = nameListOfPerformTicketRank;
    }

    public int[] getAmountListOfPerformTicketRank() {
        return amountListOfPerformTicketRank;
    }

    public void setAmountListOfPerformTicketRank(int[] amountListOfPerformTicketRank) {
        this.amountListOfPerformTicketRank = amountListOfPerformTicketRank;
    }

    public String[] getNameListOfPerformMoneyRank() {
        return nameListOfPerformMoneyRank;
    }

    public void setNameListOfPerformMoneyRank(String[] nameListOfPerformMoneyRank) {
        this.nameListOfPerformMoneyRank = nameListOfPerformMoneyRank;
    }

    public int[] getAmountListOfPerformMoneyRank() {
        return amountListOfPerformMoneyRank;
    }

    public void setAmountListOfPerformMoneyRank(int[] amountListOfPerformMoneyRank) {
        this.amountListOfPerformMoneyRank = amountListOfPerformMoneyRank;
    }

    public String[] getNameListOfPerformTypeTicketRank() {
        return nameListOfPerformTypeTicketRank;
    }

    public void setNameListOfPerformTypeTicketRank(String[] nameListOfPerformTypeTicketRank) {
        this.nameListOfPerformTypeTicketRank = nameListOfPerformTypeTicketRank;
    }

    public int[] getAmountListOfPerformTypeTicketRank() {
        return amountListOfPerformTypeTicketRank;
    }

    public void setAmountListOfPerformTypeTicketRank(int[] amountListOfPerformTypeTicketRank) {
        this.amountListOfPerformTypeTicketRank = amountListOfPerformTypeTicketRank;
    }

    public String[] getNameListOfPerformTypeMoneyRank() {
        return nameListOfPerformTypeMoneyRank;
    }

    public void setNameListOfPerformTypeMoneyRank(String[] nameListOfPerformTypeMoneyRank) {
        this.nameListOfPerformTypeMoneyRank = nameListOfPerformTypeMoneyRank;
    }

    public int[] getAmountListOfPerformTypeMoneyRank() {
        return amountListOfPerformTypeMoneyRank;
    }

    public void setAmountListOfPerformTypeMoneyRank(int[] amountListOfPerformTypeMoneyRank) {
        this.amountListOfPerformTypeMoneyRank = amountListOfPerformTypeMoneyRank;
    }

    public String[] getNameListOfPerformKeysTicketRank() {
        return nameListOfPerformKeysTicketRank;
    }

    public void setNameListOfPerformKeysTicketRank(String[] nameListOfPerformKeysTicketRank) {
        this.nameListOfPerformKeysTicketRank = nameListOfPerformKeysTicketRank;
    }

    public int[] getAmountListOfPerformKeysTicketRank() {
        return amountListOfPerformKeysTicketRank;
    }

    public void setAmountListOfPerformKeysTicketRank(int[] amountListOfPerformKeysTicketRank) {
        this.amountListOfPerformKeysTicketRank = amountListOfPerformKeysTicketRank;
    }

    public String[] getNameListOfPerformKeysMoneyRank() {
        return nameListOfPerformKeysMoneyRank;
    }

    public void setNameListOfPerformKeysMoneyRank(String[] nameListOfPerformKeysMoneyRank) {
        this.nameListOfPerformKeysMoneyRank = nameListOfPerformKeysMoneyRank;
    }

    public int[] getAmountListOfPerformKeysMoneyRank() {
        return amountListOfPerformKeysMoneyRank;
    }

    public void setAmountListOfPerformKeysMoneyRank(int[] amountListOfPerformKeysMoneyRank) {
        this.amountListOfPerformKeysMoneyRank = amountListOfPerformKeysMoneyRank;
    }

    public int[] getAmountListOfCustomerType() {
        return amountListOfCustomerType;
    }

    public void setAmountListOfCustomerType(int[] amountListOfCustomerType) {
        this.amountListOfCustomerType = amountListOfCustomerType;
    }

    public int[] getAmountListOfCustomerTypeTicket() {
        return amountListOfCustomerTypeTicket;
    }

    public void setAmountListOfCustomerTypeTicket(int[] amountListOfCustomerTypeTicket) {
        this.amountListOfCustomerTypeTicket = amountListOfCustomerTypeTicket;
    }

    public double[] getAmountListOfCustomerTypeMoney() {
        return amountListOfCustomerTypeMoney;
    }

    public void setAmountListOfCustomerTypeMoney(double[] amountListOfCustomerTypeMoney) {
        this.amountListOfCustomerTypeMoney = amountListOfCustomerTypeMoney;
    }

    public String[] getPreferenceNameListForTicket() {
        return preferenceNameListForTicket;
    }

    public void setPreferenceNameListForTicket(String[] preferenceNameListForTicket) {
        this.preferenceNameListForTicket = preferenceNameListForTicket;
    }

    public double[] getPreferenceAmountListForTicket() {
        return preferenceAmountListForTicket;
    }

    public void setPreferenceAmountListForTicket(double[] preferenceAmountListForTicket) {
        this.preferenceAmountListForTicket = preferenceAmountListForTicket;
    }

    public String[] getPreferenceNameListForMoney() {
        return preferenceNameListForMoney;
    }

    public void setPreferenceNameListForMoney(String[] preferenceNameListForMoney) {
        this.preferenceNameListForMoney = preferenceNameListForMoney;
    }

    public double[] getPreferenceAmountListForMoney() {
        return preferenceAmountListForMoney;
    }

    public void setPreferenceAmountListForMoney(double[] preferenceAmountListForMoney) {
        this.preferenceAmountListForMoney = preferenceAmountListForMoney;
    }

    double[] preferenceAmountListForTicket;
    String[] preferenceNameListForMoney;
    double[] preferenceAmountListForMoney;
    //市场占有率部分暂不做







}
