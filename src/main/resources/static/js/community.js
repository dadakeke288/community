$( function() {
    $("#ques-comment-submit").click(function () {
        var questId = $("#question-id").val();
        var content = $("#comment-text").val();
        $.ajax({
           type:"POST",
           url:"/comment",
            contentType:"application/json",
           data:JSON.stringify({
               "parentId":questId,
                "content":content,
                "type":1
            }),
            success:function (responce) {
                if (responce.code == 200){
                    $("#comment-section").hide();
                }else if(responce.code == 2003){
                    var isAccepted = confirm(responce.message);
                    if (isAccepted){
                        window.open("http://github.com/login/oauth/authorize?client_id=29d0ee78df0b8f0c1783&redirect_url=localhost:8443&scope=user&state=1");
                        window.localStorage.setItem("closable",1);
                    }
                }else{
                    console.log(responce);
                }

            },
            dataType:"json"
        });
    });
});