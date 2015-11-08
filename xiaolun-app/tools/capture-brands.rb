require 'open-uri'
require 'nokogiri'

def func letter

	uri = "http://www.autohome.com.cn/grade/carhtml/#{letter}.html"
	content = ''

	OpenURI::open_uri(uri) do |resp|
		if resp.status[0] == '200'
			content = resp.read
		end
	end

	html = Nokogiri::HTML(content, nil, 'gb2312')
	titles = html.css('dd div.h3-tit')

	titles.each do |title|
		puts "insert into xlap.tm_sub_brand (`bd_name`) values ('#{title.text}');"
	end

end

for c in 'A'..'Z'
	func c
end