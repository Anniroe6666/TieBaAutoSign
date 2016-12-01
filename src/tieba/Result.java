package tieba;

public enum Result {
	/***
	 * {"error":"�ף���֮ǰ�Ѿ�ǩ����","no":1101,"data":{"msg":"�ף���֮ǰ�Ѿ�ǩ����"}}
	 * {"error":"��������δ�ɹ����鷳����һ����","no":11000,"data":{"msg":"��������δ�ɹ����鷳����һ����"}}
	 * {"error":"ǩ��̫Ƶ���˵㣬��ϢƬ�������ɣ���","no":1010,"data":{"msg":"ǩ��̫Ƶ���˵㣬��ϢƬ�������ɣ���"}}
	 * no 0 error {"error":"3","no":0,"data":{"forum_sign_info_data":{"generate_time":0,"is_filter":false,"is_on":true,"sign_day_count":1,"member_count":103071,"sign_rank":7,"dir_rate":"0.1","sign_count":6407},"add_sign_data":{"is_block":0,"sign_version":2,"finfo":{"current_rank_info":{"sign_count":3831},"forum_info":{"forum_id":7665648,"forum_name":"�ٶȳɾ�"}},"errno":0,"uinfo":{"total_sign_num":860,"cont_sign_num":1,"hun_sign_num":81,"is_sign_in":1,"total_resign_num":0,"cout_total_sing_num":860,"user_id":172363399,"user_sign_rank":3831,"is_org_name":0,"sign_time":1469005189},"errmsg":"success"},"msg":"3"}}


	 
	 *
	 */
	RED("�Ѿ�ǩ������",1101),GREEN("ǩ���ɹ�",0),YELLOW("����δ�ɹ�",11000),BLANK("ǩ��̫Ƶ����,��Ȼ�����",1010);
	private String name;
	private int index;
	private Result(){
		
	}
	private Result(String name,int index){
		this.name = name;
		this.index = index;
	}
	
	public static String getName(int index){
		String name = null;
		for(Result c : Result.values()){
			if(c.getIndex() == index){
				name = c.name;
			}
		}
		return name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}
