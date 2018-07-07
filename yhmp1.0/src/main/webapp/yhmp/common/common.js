/**
 * 合并json
 * @param des 填写{}
 * @param src
 * @param override
 * @returns
 */
function extend(des, src, override){
	if(src instanceof Array){
		for(var i = 0, len = src.lenth;i<len;i++)extend(des,src[i],override);
	}
	for(var i in src){
		if(override || !(i in des)){
			des[i] = src[i];
		}
	}
	return des;
}