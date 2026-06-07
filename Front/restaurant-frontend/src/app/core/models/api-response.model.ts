export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  error?: string;
  timestamp?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
