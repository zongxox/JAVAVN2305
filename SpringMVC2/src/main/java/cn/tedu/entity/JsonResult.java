package cn.tedu.entity;


public class JsonResult {
    //狀態碼
    //1代表OK,0表示Error失敗
    private Integer state = 1;

    //狀態信息
    private String message = "OK";

    //封裝正確的查詢結果
    private Object data;

    //無參數構造器
    //默認存在,會什麼還要聲明出來?
    //因為接下來要使用有參數的JsonResult構造器
    //如果說你沒有去添加任何的構造器方法,那當然系統會去幫我們添加一個無參數構造方法
    //自己定義了構造器,系統就不會再幫你創建
    //簡單的說法就是聲名了有參數構造器,會把無參數構造器覆蓋掉,所有要把無參數她創建出來
    public JsonResult(){}



    //有參數JsonResult構造器
    //this.message = message; 右邊message是參數,參數再把message賦值給左邊message實體變量,this當前對象
    public JsonResult(String message){
        this.message = message;
    }

    public JsonResult(Integer state,String message){
        //this(message);相當於是this.message = message; 就是調用上面構造器方法
        //如果是多個參數這樣寫this(xxxx,xxxx,xxxx,xxxx)
        this.message = message;//一個參數就這樣寫就好
        this. state = state;
    }

    public JsonResult(Object data){

        this.data = data;
    }

    //當出現異常時,可以通過此構造方法對異常信息進行封裝
    //處理異常
    //Throwable 它包含異常和錯誤,只要是錯誤都是Throwable
    public JsonResult(Throwable exception){
        this(0, exception.getMessage());

    }
    //下面是get和set
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
