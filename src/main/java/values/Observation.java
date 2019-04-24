package values;

import java.time.LocalDateTime;

public class Observation {
	private LocalDateTime start;
	private LocalDateTime end;
	private String content;

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	LocalDateTime ldt = LocalDateTime.now();
//	LocalDateTime t = ldt.parse("2014-05-05T18:10:50.123");
									

}
