$(function () {
        //将所有表单元素设置为readonly
        $('form[readonly]').find('input,select').attr('readonly', 'readonly');
        $('form[disabled]').find('input,select').attr('disabled', 'disabled');
    }
);

$.fn.disabled = function () {
    $(this).attr('disabled', 'disabled');
    $(this).find('input,select').attr('disabled', 'disabled');
};

$.fn.readonly = function () {
    $(this).attr('readonly', 'readonly');
    $(this).find('input,select').attr('readonly', 'readonly');
};

$.fn.lock=function () {
    $(this).disabled();
    $(this).readonly();
};
$.fn.unDisabled = function () {
    $(this).removeAttr('disabled');
    $(this).find('input,select').removeAttr('disabled');
};

$.fn.unReadonly = function () {
    $(this).removeAttr('readonly');
    $(this).find('input,select').removeAttr('readonly');
};

$.fn.unLock=function () {
    $(this).unDisabled();
    $(this).unReadonly();
};
$.fn.reset=function () {
    $(this)[0].reset();
    $(this).find('[type="hidden"]').val('');
};