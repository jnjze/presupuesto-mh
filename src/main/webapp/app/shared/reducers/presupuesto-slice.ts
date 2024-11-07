import { createSlice, PayloadAction, createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import axios from 'axios';
import { IPresupuesto, defaultValue } from 'app/shared/model/presupuesto.model';
import { createEntitySlice, EntityState, serializeAxiosError } from './reducer.utils';
import { cleanEntity } from '../util/entity-utils';

const apiUrl = 'api/presupuestos';

const initialState: EntityState<IPresupuesto> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export const createEntity = createAsyncThunk(
  'presupuesto/create_entity',
  async (entity: IPresupuesto, thunkAPI) => {
    const result = await axios.post<IPresupuesto>(apiUrl, cleanEntity(entity));
    return result;
  },
  { serializeError: serializeAxiosError },
);

const presupuestoForm = createEntitySlice({
  name: 'presupuestoForm',
  initialState,
  extraReducers(builder) {
    builder
      .addMatcher(isFulfilled(createEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(createEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = presupuestoForm.actions;

export default presupuestoForm.reducer;
