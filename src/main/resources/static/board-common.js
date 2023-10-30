
let submitFlag = false;
const onsubmitListenter = function(){
        if(submitFlag){
        return false
    }else{
        submitFlag = true
        document.boardForm.submit()
    }
}