
let submitFlag = false;
const onsubmitListenter = function(){
        if(submitFlag){
        return false
    }else{
        submitFlag = true
        document.boardForm.submit()
    }
}

const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: content
});