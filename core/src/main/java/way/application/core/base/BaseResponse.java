package way.application.core.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
final public class BaseResponse<T> {

	private final int status;
	private final String message;
	private final T data;

	public static <T> BaseResponse<T> ofSuccess(T data) {
		return new BaseResponse<>(200, "SUCCESS", data);
	}

	public static <T> BaseResponse<T> ofFail(int status, String message) {
		return new BaseResponse<>(status, message, null);
	}
}
