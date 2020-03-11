$( function() {
    $("#ques-comment-submit").on("click",function () {
        var questId = $("#question-id").val();
        var content = $("#comment-text").val();
        comment2target(questId,1,content);
    });
    $(".comment-2nd-submit").on("click",function () {
        debugger;
        var commentId = this.getAttribute("data-id");
        var content = $("#comment2-"+commentId).val();
        comment2target(commentId,2,content);
        var comments = $("#comment-" + id);
    });
    $(".comment-2nd-show").on("click",function () {
        // debugger;
        var id = this.getAttribute("data-id");
        var comments = $("#comment-" + id);
        var collapse = this.getAttribute("data-collapse");
        if (collapse) {
            // 折叠二级评论
            comments.removeClass("in");
            this.removeAttribute("data-collapse");
            this.classList.remove("active");
        } else {
            var subCommentContainer = $("#comment-" + id);
            if (subCommentContainer.children().length != 1) {
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                this.setAttribute("data-collapse", "in");
                this.classList.add("active");
            }else{
                server2comment(id);
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                this.setAttribute("data-collapse", "in");
                this.classList.add("active");
            }

        }
    });

});

function comment2target(targetId,type,content) {
    if (!content){
        alert("回复不能为空");
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (responce) {
            if (responce.code == 200){
                // $("#comment-section").hide();
                console.log(responce);
                window.location.reload();
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
}
function server2comment(id) {
    $.getJSON("/comment/"+id,function (data) {
        // console.log(data);

        $.each(data.data.reverse(),function (index,comment) {
            // debugger;
            var mediaLeftElement = $("<div/>", {
                "class": "media-left"
            }).append($("<img/>", {
                "class": "media-object img-rounded",
                "src": comment.user.avatarUrl
            }));

            var mediaBodyElement = $("<div/>", {
                "class": "media-body"
            }).append($("<h5/>", {
                "class": "media-heading",
                "html": comment.user.name
            })).append($("<div/>", {
                "html": comment.content
            })).append($("<div/>", {
                "class": "menu"
            }).append($("<span/>", {
                "class": "pull-right",
                "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
            })));

            var mediaElement = $("<div/>", {
                "class": "media"
            }).append(mediaLeftElement).append(mediaBodyElement);

            var commentElement = $("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
            }).append(mediaElement);

            $("#comment-"+id).prepend(commentElement);
        });
    });
}